<%--
  Created by IntelliJ IDEA.
  User: dang
  Date: 14/11/2023
  Time: 22:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ page import="java.net.URLEncoder" %>
<% request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <style>
        .navbar-nav .nav-item {
            margin-right: 100px; /* Đặt khoảng cách 50px giữa các thẻ li */
        }
        body{margin-top:20px;
            background-color:#f2f6fc;
            color:#69707a;
        }
        .img-account-profile {
            height: 10rem;
        }
        .rounded-circle {
            border-radius: 50% !important;
        }
        .card {
            box-shadow: 0 0.15rem 1.75rem 0 rgb(33 40 50 / 15%);
        }
        .card .card-header {
            font-weight: 500;
        }
        .card-header:first-child {
            border-radius: 0.35rem 0.35rem 0 0;
        }
        .card-header {
            padding: 1rem 1.35rem;
            margin-bottom: 0;
            background-color: rgba(33, 40, 50, 0.03);
            border-bottom: 1px solid rgba(33, 40, 50, 0.125);
        }
        .form-control, .dataTable-input {
            display: block;
            width: 100%;
            padding: 0.875rem 1.125rem;
            font-size: 0.875rem;
            font-weight: 400;
            line-height: 1;
            color: #69707a;
            background-color: #fff;
            background-clip: padding-box;
            border: 1px solid #c5ccd6;
            -webkit-appearance: none;
            -moz-appearance: none;
            appearance: none;
            border-radius: 0.35rem;
            transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
            }
        a {
            color: black;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand"  href="#">
        <form action="/Servlet?action=Home" method="post" >
            <button  class="nav-link" >Money Lover<span class="sr-only">(current)</span></button>
        </form>
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav " style="margin: auto">
            <li class="nav-item active">
                <form action="/Servlet?action=Home" method="post" >
                    <button  class="nav-link" >Home<span class="sr-only">(current)</span></button>
                </form>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Link</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Link</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Link</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Profile
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="/Servlet?action=update">Update Profile</a>
                    <a class="dropdown-item" href="#">Delete account</a>
                    <div class="dropdown-divider"></div>
                    <form method="post" action="/Servlet?action=Logout">
                        <button type="submit" class="dropdown-item">Logout</button>
                    </form>
                </div>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>
<div class="container-xl px-4 mt-4">
    <hr class="mt-0 mb-4">
    <div class="row">
        <div class="col-xl-4">
            <!-- Profile picture card-->
            <div class="card mb-4 mb-xl-0">
                <div class="card-header">Profile Picture</div>
                <div class="card-body text-center">
                    <img width="320px" height="200px" src="${sessionScope['user'].getImage()}" alt="picture">
                    <div class="small font-italic text-muted mb-4">JPG only</div>
                </div>

            </div>
            <form method="post" action="/Servlet?action=uploadPicture" enctype="multipart/form-data">
                <input type="file" name="file">
                <button class="btn btn-primary" type="submit">Upload new image</button>
            </form>
        </div>
        <div class="col-xl-8">
            <div class="card mb-4">
                <div class="card-header">Account Details</div>
                <div class="card-body">
                    <form action="/Servlet?action=UpdateProfile" method="post" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label class="small mb-1" for="name">Full name </label>
                            <input class="form-control" id="name" type="text" name="fullName" placeholder="Enter your name" value="${sessionScope['user'].getFullName()}">
                        </div>
                        <div class="row gx-3 mb-3">
                            <div class="col-md-6">
                                <label class="small mb-1"  for="gender">Gender</label>
                                <input class="form-control" id="gender" name="gender" type="text" placeholder="Enter your gender" value="${sessionScope['user'].getGender()}">
                            </div>
                            <div class="col-md-6">
                                <label class="small mb-1" for="Birthdate">Birthdate</label>
                                <input class="form-control" name="birthdate" id="Birthdate" type="date" value="${sessionScope['user'].getBirthdate()}">
                            </div>
                        </div>
                        <div class="mb-3">
                            <label class="small mb-1" for="phoneNumber">Phone Number</label>
                            <input class="form-control" id="phoneNumber" name="phoneNumber" type="number" placeholder="Enter your phone number" value="${sessionScope['user'].getPhoneNumber()}">
                        </div>
                        <button class="btn btn-primary" type="submit">Save changes</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"
        integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+"
        crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>

</html>
