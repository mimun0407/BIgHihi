<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modal Example</title>
    <!-- Link to Bootstrap CSS (for modal styling) -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>

<!-- Button to trigger the modal -->
<button type="button" class="btn btn-primary" id="openModalBtn">
    Đăng ký
</button>

<!-- The Modal -->
<div class="modal" tabindex="-1" role="dialog" id="registrationModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Form Đăng Ký</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- Your registration form goes here -->
                <form id="registrationForm">
                    <!-- Form fields go here -->
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required>
                    <br>
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                    <br>
                    <button type="submit" class="btn btn-primary">Đăng Ký</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Link to jQuery and Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

<!-- Your custom script for handling the modal and Ajax -->
<script>
    $(document).ready(function () {
        // Show the modal when the button is clicked
        $("#openModalBtn").click(function () {
            $("#registrationModal").modal("show");
        });

        // Handle the form submission using Ajax
        $("#registrationForm").submit(function (event) {
            // Prevent the default form submission
            event.preventDefault();

            // Your Ajax logic goes here
            // For example, you can use $.ajax() from jQuery
            $.ajax({
                url: "your_registration_endpoint.php",
                method: "POST",
                data: $(this).serialize(),
                success: function (response) {
                    // Handle the success response
                    console.log(response);
                    // Display success message in the modal body
                    $(".modal-body").html("<p>Đăng ký thành công!</p>");
                    // Optionally, you can close the modal after a delay
                    setTimeout(function () {
                        $("#registrationModal").modal("hide");
                    }, 2000);
                },
                error: function (error) {
                    // Handle the error
                    console.error(error);
                }
            });
        });
    });
</script>

</body>
</html>
