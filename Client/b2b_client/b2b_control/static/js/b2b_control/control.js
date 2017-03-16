$(document).ready(function() {
    var log_zone = $('.log-zone');
    $('.clear-log').on('click', function () {
        log_zone.val('');
    });
    $('.direction').on('click', function () {
        var speed = $('.speed').val();
        var data = JSON.parse('{"characters":"'+ $(this).val() +'", "value":"'+ speed +'"}');
        $.ajax({
            type: "POST",
            url: window.command_url,
            data: data,
            dataType : 'json',   //you may use jsonp for cross origin request
            complete: function(){
                var text = "Direction: ";

                switch(data['characters']) {
                    case "o":
                        text += "Forward";
                        break;
                    case "k":
                        text += "Left";
                        break;
                    case "l":
                        text += "Backward";
                        break;
                    case "m":
                        text += "Right";
                        break;
                    case "a" :
                        text += "Rotate left";
                        break;
                    case "e":
                        text += "Rotate right";
                        break;
                    default:
                        text += "";
                        break;
                }

                text += ", Vitesse: " + data['value'];

                if (data['characters'] == "STOP") {
                    text = "STOP";
                }

                log_zone.val(log_zone.val() + text + '\n');

                if(log_zone.length)
                     log_zone.scrollTop(log_zone[0].scrollHeight - log_zone.height());
            }

        });
    });

    setInterval(function(){
        if ($('.camera').is(':checked')) {
            var myImg = $('.img-stream');
            var mySrc = window.image_url;
            var d = new Date();
            myImg.attr('src', mySrc + d.getTime());
        }
    }, 650);


});

