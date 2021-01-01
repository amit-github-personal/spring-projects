package co.springcoders.sseevents.SSEExample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@SpringBootApplication
public class SseExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SseExampleApplication.class, args);
	}

}


@RestController
class EventHandler {

	private List<SseEmitter> sseEmitterList = new CopyOnWriteArrayList<>();

	/**
	 * <p>The main event streaming endpoint. This endpoint will continuously emit
	 * the emitters and stream the events to the client and will add those events
	 * in a list to keep a record of sent events. At certain point, if you want to trigger
	 * some data to a specific event, iterate through the list and send the event to each or
	 * specific emitter.</p>
	 * @return emitter
	 */
	@GetMapping(value = "/event/subscribe")
	public SseEmitter getEmitters(){
		/*Create SseEmitter with max long timeout value*/
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
		try {
			emitter.send(SseEmitter.event().name("Init Event").data("Initialization is successful").build());
		} catch (IOException e) {
			e.printStackTrace();
		}
		emitter.onCompletion(() -> sseEmitterList.remove(emitter));
		sseEmitterList.add(emitter);
		return emitter;
	}

	@PostMapping("/event/add")
	public void emitEventDataToAllEmitters(@RequestParam String eventName){
		for (SseEmitter emitter: sseEmitterList ) {
			try {
				emitter.send(SseEmitter.event().name("notification").data(eventName));
			} catch (IOException e) {
				sseEmitterList.remove(emitter);
			}
		}
	}
}