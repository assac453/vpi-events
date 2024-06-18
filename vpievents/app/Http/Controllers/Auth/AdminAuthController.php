<?php


namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;
use App\Mail\VerifyEmail;
use App\Models\Role;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Mail;

class AdminAuthController extends Controller
{
    public function showLoginForm()
    {
        return view('auth.admin-login');
    }

    public function login(Request $request)
    {
        $request->validate([
            'email' => 'required|email',
            'password' => 'required|min:6',
        ]);

        if (Auth::attempt($request->only('email', 'password'))) {
            return redirect()->intended(route('admin.index'));
        }
        return back()->withErrors([
            'email' => 'Предоставленные учетные данные не соответствуют нашим записям.',
        ]);
    }

    public function showRegisterForm()
    {
        return view('auth.admin-register');
    }

    public function register(Request $request)
    {
//        $role = Role::where('name', 'admin')->first();

        $request->validate([
            'email' => 'required|email|unique:users',
            'password' => 'required|min:6|confirmed',
        ]);

        $user = User::create([
            'email' => $request->email,
            'password' => Hash::make($request->password),
//            'role_id' => $role->id,
        ]);

        Auth::login($user);

        return redirect(route('admin.index'));
    }

    public function logout()
    {
        Auth::logout();
        return redirect(route('admin.login'));
    }

    public function changePassword(Request $request)
    {
        // Валидация данных
        $request->validate([
            'current_password' => 'required',
            'new_password' => 'required|string|min:8|confirmed',
        ]);

        // Получение текущего пользователя
        $user = Auth::user();

        // Проверка текущего пароля
        if (!Hash::check($request->current_password, $user->password)) {
            return redirect()->back()->withErrors(['current_password' => 'Текущий пароль неверен']);
        }

        // Изменение пароля
        $user->password = Hash::make($request->new_password);
        $user->save();

        return redirect()->back()->with('success', 'Пароль успешно изменен');
    }

    public function confirmEmail(Request $request)
    {
        // Получить пользователя, для которого нужно подтвердить адрес электронной почты
        $user = $request->user();

        // Создать экземпляр письма VerifyEmail с пользователем в качестве аргумента
        $mail = new VerifyEmail($user);

        // Отправить письмо
        Mail::to($user->email)->send($mail);
        return redirect()->back()->with('success', 'Письмо для подтверждения почты было успешно отправлено.');
    }

}
