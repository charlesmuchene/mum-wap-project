<%--suppress ELValidationInJSP --%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Task list</title>
  <link rel="stylesheet" type="text/css" href="resources/styles/tasks.css"
        media="screen" />
  <script src="resources/scripts/jquery-2.0.3.js"></script>
  <script src="resources/scripts/jquery-tmpl.js"></script>
  <script src="resources/scripts/jquery.validate.js"></script>
  <script src="resources/scripts/jquery-serialization.js"></script>
  <script src="resources/scripts/tasks-controller.js"></script>
  <script src="resources/scripts/date.js"></script>
  <script src="resources/scripts/jquery.csv-0.71.js"></script>
</head>
<body>
<header>
  <span>Task list</span>
</header>
<main  id="taskPage">
  <section id="taskCreation" class="not">
    <form id="taskForm">
      <input type="hidden" name="id"/>
      <div>
        <label>Task</label> <input type="text" required="required"
                                   name="task" class="large" placeholder="Breakfast at Tiffanys" maxlength="200"  />
      </div>
      <div>
        <label>Required by</label> <input type="date" required="required"
                                          name="requiredBy" />
      </div>
      <div>
        <label>Category</label> <select name="category">
        <option value="Personal">Personal</option>
        <option value="Work">Work</option>
      </select>
      </div>
      <nav>
        <a href="#" id="saveTask">Save task</a>
        <a href="#" id="clearTask">Clear task</a>
      </nav>
    </form>
  </section>
  <section>
    <table id="tblTasks">
      <colgroup>
        <col width="40%">
        <col width="15%">
        <col width="15%">
        <col width="30%">
      </colgroup>
      <thead>
      <tr>
        <th>Name</th>
        <th>Due</th>
        <th>Category</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
    <nav>
      <a href="#" id="btnAddTask">Add task</a>
    </nav>
  </section>
  <section id="csvImport">
    <div>
      <label for="task">Import tasks from a CSV file</label>
      <input  type="file" id="importFile" name="importFile"/>
    </div>
  </section>

</main>
<footer>You have <span id="taskCount"></span> tasks</footer>
</body>
<script>
  function initScreen() {
    $(document).ready(function() {
      tasksController.init($('#taskPage'), function() {
        tasksController.loadTasks();
      });
    });
  }
  if (window.indexedDB) {
    $.getScript( "scripts/tasks-indexeddb.js" )
            .done(function( script, textStatus ) {
              initScreen();
            })
            .fail(function( jqxhr, settings, exception ) {
              console.log( 'Failed to load indexed db script' );
            });
  } else if (window.localStorage) {
    $.getScript( "scripts/tasks-webstorage.js" )
            .done(function( script, textStatus ) {
              initScreen();
            })
            .fail(function( jqxhr, settings, exception ) {
              console.log( 'Failed to load web storage script' );
            });
  }
</script>

<script id="taskRow" type="text/x-jQuery-tmpl">
<tr>
	<td {{if complete == true}}class="taskCompleted"{{/if}}>${task}</td>
	<td {{if complete == true}}class="taskCompleted"{{/if}}><time datetime="${requiredBy}">${requiredBy}</time></td>
	<td {{if complete == true}}class="taskCompleted"{{/if}}>${category}</td>
	<td>
		<nav>
			{{if complete != true}}
				<a href="#" class="editRow" data-task-id="${id}">Edit</a>
				<a href="#" class="completeRow" data-task-id="${id}">Complete</a>
			{{/if}}
			<a href="#" class="deleteRow" data-task-id="${id}">Delete</a>
		</nav>
	</td>
</tr>
</script>

</html>