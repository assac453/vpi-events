@vite(['resources/sass/app.scss', 'resources/js/app.js'])

    <!DOCTYPE html>
<html lang="en">
<head>
    <title>QR-код мероприятия</title>
    <style>
        body, html {
            height: 100%;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #f5f5f5;
        }
        .qrcode-container {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            width: 100%;
            height: 100%;
            max-width: 100vw;
            max-height: 100vh;
            padding: 20px;
            box-sizing: border-box;
        }
        .qrcode-container img {
            max-width: 100%;
            max-height: 100%;
        }
    </style>
</head>
<body>
<div class="qrcode-container">
    <img src="{{ $qrcode }}" alt="QR Code">
    <a href="{{ $downloadUrl }}" download="qrcode.png" class="btn btn-primary mt-3">Скачать QR-код</a>
</div>
</body>
</html>
