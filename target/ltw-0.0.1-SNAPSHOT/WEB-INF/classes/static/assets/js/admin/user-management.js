$(document).ready(function() {
    // Setup - add a text input to each footer cell
    $('#table tfoot th').each( function () {
        const title = $(this).text();
        $(this).html( '<input type="text" class="form-control" placeholder="Search '+title+'" />' );
    } );


    // DataTable
    const table = $('#table').DataTable({
        initComplete: function () {
            // Apply the search
            this.api().columns().every( function () {
                const that = this;
                $( 'input', this.footer() ).on( 'keyup change clear', function () {
                    if ( that.search() !== this.value ) {
                        that
                            .search( this.value )
                            .draw();
                    }
                } );
            } );
        }
    });

    const $edit = $('#edit');
    const $delete = $('#delete');
    const $selectAll = $('#selectAll');
    $edit.click(function () {
        $('input[name=userId]').each(function (index,item) {
            if(item.checked) {
                location.href = location.origin + "/admin/user-management/" + item.value;
            }
        });

    })

    $delete.click(function () {
        if(confirm("Xác nhận xoá")) $('#form-table').submit();
    })

    $selectAll.change(function () {
        $edit.addClass("disabled");

        const $selector = $('#table tbody tr fieldset');
        if(this.checked){
            $selector.children().each(function (index,item) {
                item.checked = true;
            })
        }
        else {
            $selector.children().each(function (index,item) {
                item.checked = false;
            })}


        let checked = 0;
        $('input[name=userId]').each(function (index,item) {
            if(item.checked) {
                checked++;
            }
        });
        if(checked >= 1){
            $delete.removeClass("disabled");
        }else {
            $delete.addClass("disabled");
        }
    })

    $('#add').click(function (event) {
        const $newUser = $('#new-user');
        if($newUser.length){
            event.preventDefault();
            $newUser.removeClass('d-none');
        }
    })


    // check if email isExit in table
    const $btnSubmit = $('#btn-submit');
    $('#form').submit(function (event) {
        checkPassword(event);
    });

    const $btnChangePassword = $('#btnChangePassword');
    $('#re-password').on('keyup change clear',function () {
        const $rePasswordError = $('#repass-error');
      if( $('#password').val() != this.value){
          $rePasswordError.text("Mật khâu không khớp");
          $rePasswordError.removeClass("d-none");
          $btnChangePassword.addClass("disabled");
      }else {
          $rePasswordError.addClass("d-none");
          $btnChangePassword.removeClass("disabled");
      }
    })

    $('#password').on('keyup change clear',function () {
        $('#re-password').val("");
        $btnChangePassword.addClass("disabled");
    })




    $('#file').change(function (event) {
        const $avtError = $('#avt-error');
        if (!isValidImage()) {
            $avtError.removeClass("d-none");
            $avtError.text("Vui lòng chọn file có định dạng ảnh!");
            $btnSubmit.addClass("disabled");
        } else {
            $avtError.addClass("d-none");
            $btnSubmit.removeClass("disabled");
            fakeProcessUpload(event);
        }
    })

});