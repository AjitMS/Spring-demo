<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="CSS/homepage.css" />

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
	integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M"
	crossorigin="anonymous">

<title>Nerds' Paradise</title>
</head>
<body>

	<nav class="navbar navbar-dark bg-dark"> <span
		class="navbar-text silvertext"> Hello, ${user.fullname} ! </span>

	<ul class="nav">
		<li class="nav-item"><a class="nav-link active" href="#">Active</a></li>
		<li class="nav-item"><a class="nav-link" href="#">Link</a></li>
		<li class="nav-item"><a class="nav-link " href="#">Link</a></li>
	</ul>
	<form action="LogoutController" method="post">
		<input type="submit" class="btn btn-outline-danger" value=Logout>
	</form>
	</nav>


	<!-- listBookModal -->

	<div class="modal fade" id="listBookModal" tabindex="-1" role="dialog"
		aria-labelledby="listBookModal" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="listBookModal">Category</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="modal-body">

						<div class="container-fluid">
							<table class="table table-hover" id="booktable">

							</table>
						</div>

					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<!-- AddBookModal -->
	<div class="modal fade" id="addBookModal" tabindex="-1" role="dialog"
		aria-labelledby="addBookModal" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="addBookModal">Add new Book</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="modal-body">

						<div class="container-fluid">

							<form id="addbookform">
								<div class="form-group">
									<label for="bookname">Book Name</label> <input type="text"
										class="form-control" id="bookname" name="bookname"
										aria-describedby="bookname" placeholder="Enter book name"
										required>
								</div>
								<div class="form-group">
									<label for="bookauthor">Book Author</label> <input type="text"
										class="form-control" id="bookauthor" name="bookauthor"
										placeholder="Enter Book Author" required>
								</div>

								<div class="form-group">
									<label for="bookcategory">Select Category</label> <select
										class="form-control" id="bookcategory" name="bookcategory"
										required>
										<option>Arts</option>
										<option>Commerce</option>
										<option>Science</option>
									</select>
								</div>

								<div class="form-group">
									<label for="bookdecsription">Book Description</label>
									<textarea class="form-control" id="bookdescription"
										name="bookdescription" rows="3" required></textarea>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success btn-block"
						id="addbooksubmit">Add</button>
				</div>
			</div>
		</div>
	</div>

	<!-- UpdateBookModal -->

	<div class="modal fade" id="updateBookModal" tabindex="-1" role="dialog"
		aria-labelledby="updateBookModal" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="updateBookModal">Update Book</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="modal-body">

						<div class="container-fluid">

							<form id="updatebookform">
								<div class="form-group">

									<input type="hidden" class="form-control" id="oldbookname"
										name="oldbookname" placeholder="Enter book name" value="empty">

									<label for="ubookname">Book Name</label> <input type="text"
										class="form-control" id="ubookname" name="ubookname"
										aria-describedby="ubookname" placeholder="Enter book name"
										required>
								</div>
								<div class="form-group">
									<label for="ubookauthor">Book Author</label> <input type="text"
										class="form-control" id="ubookauthor" name="ubookauthor"
										placeholder="Enter Book Author" required>
								</div>

								<div class="form-group">
									<label for="ubookcategory">Select Category</label> <select
										class="form-control" id="ubookcategory" name="ubookcategory"
										required>
										<option>Arts</option>
										<option>Commerce</option>
										<option>Science</option>
									</select>
								</div>

								<div class="form-group">
									<label for="ubookdecsription">Book Description</label>
									<textarea class="form-control" id="ubookdescription"
										name="ubookdescription" rows="3" required></textarea>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success btn-block"
						id="updatebooksubmit">Update</button>
				</div>
			</div>
		</div>
	</div>



	<!-- ShowBookModal -->

	<div class="modal fade" id="showBookModal" tabindex="-1" role="dialog"
		aria-labelledby="showBookModal" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="showBookModal">Category</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">

					<div class="container-fluid">
						<table class="table  table-hover" id="showbooktable">

						</table>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" id= "updatebooksubmit" class="btn btn-secondary" data-dismiss="modal">Close</button>

			</div>
		</div>
	</div>


	<!-- Cards -->
	<div
		class="col-md-10
 justify-content-right container container-margin ">
		<div class="row">
			<div class="col-md-4 col-sm-12">
				<div class="card">
					<h4 class="card-header">Arts</h4>
					<div class="card-body">
						<h4 class="card-title">Arts Section</h4>
						<p class="card-text">With supporting text below as a natural
							lead-in to additional content.</p>
						<a href="#" class="btn btn-primary" data-toggle="modal"
							data-target="#listBookModal" data-whatever="Arts">List Books</a>
					</div>
				</div>
			</div>
			<div class="col-md-4 col-sm-12">
				<div class="card">
					<h4 class="card-header">Commerce</h4>
					<div class="card-body">
						<h4 class="card-title">Commerce Section</h4>
						<p class="card-text">With supporting text below as a natural
							lead-in to additional content.</p>
						<a href="#" class="btn btn-primary" data-toggle="modal"
							data-target="#listBookModal" data-whatever="Commerce">List
							Books</a>
					</div>
				</div>
			</div>

			<div class="col-md-4 col-sm-12">
				<div class="card">
					<h4 class="card-header">Science</h4>
					<div class="card-body">
						<h4 class="card-title">Science Section</h4>
						<p class="card-text">With supporting text below as a natural
							lead-in to additional content.</p>
						<a href="#" class="btn btn-primary" data-toggle="modal"
							data-target="#listBookModal" data-whatever="Science">List
							Books</a>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col" align="right">
				<br /> <input type="button" class="btn btn-outline-success btn-lg"
					value="+" data-toggle="modal" data-target="#addBookModal"
					data-whatever="Add new Book">
			</div>
		</div>
	</div>


	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

	<script src="Javascript/homepage.js"></script>


	<script src="https://code.jquery.com/jquery-3.1.1.min.js"
		integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
		crossorigin="anonymous"></script>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
		integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
		crossorigin="anonymous"></script>

	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"
		integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1"
		crossorigin="anonymous"></script>
</body>
</html>