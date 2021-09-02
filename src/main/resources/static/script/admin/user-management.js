const isValidImage = () => {
    return document.getElementById("file")
        .files[0]
        .name
        .match(/.(jpg|jpeg|png|gif)$/i);
}

const fakeProcessUpload = async  () => {
    // fake process (real use ajax)
    const maxPercent = 100;
    let currentPercent = 1;
    const btnSubmit  = document.getElementById("btn-submit");

    btnSubmit.disabled = true;
    const processBar = document.getElementById("process-bar");
    processBar.classList.remove("d-none");
    for(currentPercent; currentPercent <= maxPercent; currentPercent ++){
        processBar.style.width = currentPercent + "%";
        processBar.setAttribute("aria-valuenow", ""+currentPercent+"");
        processBar.textContent = currentPercent + "%";
        await new Promise(r => setTimeout(r, 50));
    }
    btnSubmit.disabled = false;
    setTimeout(() => {
        processBar.classList.add("d-none");
    },2000);
}

const checkPassword = (event) => {
    const password = document.getElementById("password");
    const rePassword = document.getElementById("re-password");
    const error = document.getElementById("repass-error");
    const btnSubmit  = document.getElementById("btn-submit");
    if(password.value !== rePassword.value){
        error.style.display="block";
        error.textContent = "Mật khẩu không khớp";
        event.preventDefault();
    }else {
        btnSubmit.disabled = true;
    }
}
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

    const $selector = $('#table tbody tr fieldset');

    $selector.on('change', 'input', function () {
        const $selectAll = $('#selectAll');
        $selectAll.checked = false;
        let countCheck = 0;
        $selector.children().each(function (index,item) {
            if(item.checked){
                countCheck++;
            }
        })
        if(countCheck === 1){
            $edit.removeClass("disabled");
        }else {
            $edit.addClass("disabled");
        }
        $delete.removeClass("disabled");
    } );



    $edit.click(function () {
        let idCurrent = 0;
        $selector.children().each(function (index,item) {
            if(item.checked){
                idCurrent = item.value;
            }
        })
        location.href = location.origin + "/admin/user-management/" + idCurrent;
    })

    $delete.click(function () {
        $('#form-table').submit();
    })

    $('#selectAll').change(function () {
        $edit.addClass("disabled");
        let check = this.checked;
        if(check){
            $delete.removeClass("disabled");
            let countCheck = 0;
            $selector.children().each(function (index,item) {
                item.checked = true;
                if(item.checked){
                    countCheck++;
                }
            })
            if(countCheck === 1){
                $edit.removeClass("disabled");
            }
        }else {
            $selector.children().each(function (index,item) {
                item.checked = false;
            })
        }
    })


    $('a[href="#collapseExample"]').click(function () {
        const $newUser = $('#new-user');
        if($newUser.hasClass("d-none")){
            $newUser.removeClass("d-none");
        }else {
            $newUser.addClass("d-none");
        }
    });


    // check if email isExit in table
    const $btnSubmit = $('#btn-submit');
    $('#email').on('keyup',function () {
        table.columns(3).search( this.value).draw();
    })

    $('#form').submit(function (event) {
        checkPassword(event);
    });

    $('#form-changePassword').submit(function (event) {
        checkPassword(event);
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