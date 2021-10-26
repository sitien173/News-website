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
    // click even each row

    const $selectAll = $('#selectAll');

    $('#add').click(function (event) {
        const $newPost = $('#new-post');
        if($newPost.length){
            event.preventDefault();
            $newPost.removeClass('d-none');
        }
    })

    $edit.click(function () {
        $('input[name=postCheckId]').each(function (index,item) {
            if(item.checked) {
                location.href = location.origin + "/admin/post-management/" + item.value;
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
        $('input[name=postCheckId]').each(function (index,item) {
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


    // check if email isExit in table
    const $btnSubmit = $('#btn-submit');
    $('#file').change(function (event) {
        const $bannerError = $('#banner-error');
        if (!isValidImage()) {
            $bannerError.removeClass("d-none");
            $bannerError.text("Vui lòng chọn file có định dạng ảnh!");
            $btnSubmit.addClass("disabled");
        } else {
            $bannerError.addClass("d-none");
            $btnSubmit.removeClass("disabled");
            fakeProcessUpload(event);
        }
    })

});