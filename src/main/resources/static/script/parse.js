'use strict';
$(document).ready(() => {

    $('#parseForm').submit((e) => {
        e.preventDefault();
        if (!confirm("출력 하시겠습니까?")) {
            return false;
        }
        let url = $('#url').val();
        let unit = $('#unit').val();
        let type = $('select[name=selValue]').val();

        $.ajax({
            url: '/parser',
            method: 'get',
            data: {url: url, type: type, unit: unit}
        }).then((response) => {
            $('#quotient').html(response.quotientString);
            $('#remainder').html(response.remainderString);
        }).catch((response) => {
            alert(response.responseJSON.message);
            return false;
        });
    });
});