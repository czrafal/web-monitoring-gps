$(document).ready(function() {
 
    //handling the click on our menu items
    $('.ui-menuitem-link').click(function(event) {
        //making sure we don't navigate
        event.preventDefault();
 
        //calculating our nav rule, this might change from app to app, this assume that your Faces Servlet is pointed to /faces/*
        var currentNav = $(this).attr('href').substr($(this).attr('href').indexOf("/faces") + 8);
        //window.alert(currentNav);
        //set the hash in our window
        window.location.hash =  currentNav;
 
    })
 
    // Bind an event to window.onhashchange that, when the history state changes,
    // gets the url from the hash and displays fetches
    // new content to be displayed.
    $(window).bind('hashchange', function(e) {
 
        // Get the hash (fragment) as a string, with any leading # removed. Note that
        // in jQuery 1.4, you should use e.fragment instead of $.param.fragment().
        var url = $.param.fragment();
 
        //call our remoteCommand passing our current nav rule
        updateNav([{name: 'currentNav', value: url}]);
 
    })
 
    // Since the event is only triggered when the hash changes, we need to trigger
    // the event now, to handle the hash the page may have loaded with.
    $(window).trigger('hashchange');
})