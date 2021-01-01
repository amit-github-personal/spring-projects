const eventField = $("#event-field")

/*Function for saving the event data
* and returning streams back to the client.
*/
function saveEventData () {
    if(eventField.val() != ""){
        $.post("http://localhost:8080/event/add?eventName="+eventField.val());
    } else {
        alert("Please enter some value")
    }
}

/**
* Enter keypress binding on the field.
*/
eventField.keypress( (event) => {
    if(event.keyCode == 13)  {
        if(eventField.val() != ""){
                $.post("http://localhost:8080/event/add?eventName="+eventField.val());
            } else {
                alert("Please enter some value")
            }
    }
});

/* Configuring the event source api
 * Once the document is ready
 */
$(document).ready( () => {
    const urlEndpoint = "http://localhost:8080/event/subscribe";
    let eventSource = new EventSource(urlEndpoint);

    eventSource.onerror = (err) => {
        console.log("Event source error occured : " + err)
    }

    eventSource.addEventListener("notification", (event) => {
       $("#notification-list").append(
                   "<li><p class='notification'>"+ event.data + "</p></li>"
               );
    });
})