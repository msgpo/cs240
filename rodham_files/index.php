<?php
$basedir = "./";

if (!array_key_exists('path', $_REQUEST))
  $_REQUEST['path'] = $basedir;

$_REQUEST['path'] = str_replace('../', '', $_REQUEST['path']);

$parentdir = dirname($_REQUEST['path']);
if ($parentdir != '.') {
  $parentdir .= '/';
} else {
  $parentdir = $basedir;
}

if ($basedir == $_REQUEST['path']) {
	$dir = $basedir;
} else {
	$dir = $basedir.$_REQUEST['path'];
}

$links = array();
if (is_dir($dir)) {
  $dircontents = glob($dir."*");
  if ($dircontents != FALSE) {
	foreach ($dircontents as $file) {
		$links[] = substr($file, 2);
	}
  }
}

$Title = "Directory Listing for $dir";

?>
<?php
	require "/users/home2/ta/cs240ta/public_html/variables.php";
	$title = 'Dr. Rodham Lecture Files';
	$navActive = NavConstants.LECTURE_FILES;
	require constant("SEMESTER_FILE_ROOT")."include/header.php";
?>

<img src="/icons/blank.gif" alt="Icon" />
Class Lectures
<hr />

<img src="/icons/back.gif" alt="[DIR]" /> <a href="?path=<?php echo rawurlencode($parentdir); ?>">Parent Directory</a><br />

<?php
foreach ($links as $link) {
	if (is_dir($link)) {
	?>
<img src="/icons/folder.gif" alt="[DIR]"> <a href="?path=<?php echo rawurlencode($link); ?>/">
<?php
	  echo basename($link);
	} else {
	  if (!preg_match("/^index.*\\.php.*$/", basename($link))) {
	?>
<img src="/icons/unknown.gif" alt="[   ]"> <a href="<?php echo $link; ?>">
<?php
	    echo basename($link);
	  }
	}
?>
</a><br />
<?php
}
?>


<?php require constant("SEMESTER_FILE_ROOT")."include/footer.php"; ?>
