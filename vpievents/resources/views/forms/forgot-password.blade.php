@vite(['resources/sass/app.scss', 'resources/js/app.js'])
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password</title>
</head>
<body>
<h1>Reset Password</h1>

@if (session('status'))
    <div>
        {{ session('status') }}
    </div>
@endif

<form method="POST" action="{{ route('admin.forgot.password.post') }}">
    @csrf

    <div>
        <label for="email">Email Address</label>
        <input id="email" type="email" name="email" value="{{ old('email') }}" required autofocus>
        @error('email')
        <span>{{ $message }}</span>
        @enderror
    </div>

    <div>
        <button type="submit">Send Password Reset Link</button>
    </div>
</form>
</body>
</html>
