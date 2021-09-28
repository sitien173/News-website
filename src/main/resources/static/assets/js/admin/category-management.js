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
    const $inputCheck = $('input[name=cateId]');
    let idCurrent = 0;


    $inputCheck.on('change', function () {
        let checked = 0;
        $selectAll.prop('checked', false);
        $inputCheck.each(function (index,item) {
            if(item.checked) checked++;
        });
        if(checked == 1){
            idCurrent = this.value;
            $edit.removeClass("disabled");
            $delete.removeClass("disabled");
        }
        else if(checked > 1){
            $delete.removeClass("disabled");
        }else {
            $edit.addClass("disabled");
            $delete.addClass("disabled");
        }
    });


    $('#add').click(function (event) {
        const $newCategory = $('#new-category');
        if($newCategory.length){
            event.preventDefault();
            $newCategory.removeClass('d-none');
        }
    })

    $edit.click(function () {
        location.href = location.origin + "/admin/category-management/" + idCurrent;
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
        $inputCheck.each(function (index,item) {
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