$ ->
  $("form").submit (event) ->
    event.preventDefault()
    $.get $("form").attr("action") + $("#query").val(), (data) ->
      console.log(data)