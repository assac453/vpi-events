<?php

namespace App\Http\Controllers\Reports;

use App\Http\Controllers\Controller;
use App\Models\Event;
use App\Models\User;
use Dompdf\Dompdf;
use Dompdf\Options;
use GPBMetadata\Google\Api\Log;
use Illuminate\Http\Request;
use TCPDF;

class ReportsController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        //
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        //
    }

    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(string $id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, string $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        //
    }

    public function events(Request $request)
    {
        $events = Event::all();
        return view('reports.events', ['events' => $events]);
    }


    public function downloadEventReport(Request $request, $eventId)
    {
        // Получаем данные о мероприятии

        $event = Event::findOrFail($eventId);
        $users = $event->users;
        // Создаем новый экземпляр Dompdf
        $options = new Options();
        $options->set('isHtml5ParserEnabled', true);
        $options->set('isPhpEnabled', true);

        $options->set('fontDir', public_path('fonts/')); // Путь к вашей директории с шрифтами
        $options->set('fontCache', public_path('fonts/')); // Путь к кэшу для шрифтов
        $options->set('defaultFont', 'DejaVuSans');
        \Illuminate\Support\Facades\Log::debug($options->getDefaultFont());

        $dompdf = new Dompdf($options);
// Добавляем содержание к PDF
        $html = '<style>
        body { font-family: DejaVu Sans, sans-serif; }
        h1 { text-align: center; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #000; padding: 8px; text-align: left; }
    </style>';
        $html .= '<h1>Отчет по мероприятию</h1>';
        $html .= '<p><strong>Название:</strong> ' . $event->name . '</p>';
        $html .= '<p><strong>Дата начала:</strong> ' . $event->begin . '</p>';
        $html .= '<p><strong>Дата окончания:</strong> ' . $event->end . '</p>';
// Добавьте больше информации о мероприятии по вашему выбору

        // Добавляем таблицу с пользователями
        $html .= '<h2>Пользователи, посетившие мероприятие</h2>';
//        $html = '';

        foreach ($users as $user) {
            $html .= '<ul>';

            $firstName = $user->personalInformation->first_name ?? 'данные не предоставлены';
            $lastName = $user->personalInformation->last_name ?? 'данные не предоставлены';
            $email = $user->email ?? 'данные не предоставлены';
            $createdAt = $user->created_at ?? 'данные не предоставлены';

            // Высчитываем возраст на основе даты рождения
            $birthDate = $user->personalInformation->birth_date ?? null;
            $age = $birthDate ? now()->diffInYears($birthDate) : 'данные не предоставлены';

            $gender = $user->personalInformation->gender ?? 'данные не предоставлены';
            $schoolId = $user->schoolInformation->school_id ?? 'данные не предоставлены';
            $class = $user->schoolInformation->class ?? 'данные не предоставлены';

            $html .= '<li><strong>Имя</strong>: ' . $firstName . ' ' . $lastName . '</li>';
            $html .= '<li><strong>Email</strong>: ' . $email . '</li>';
            $html .= '<li><strong>Дата регистрации</strong>: ' . $createdAt . '</li>';
            $html .= '<li><strong>Возраст</strong>: ' . $age . '</li>';
            $html .= '<li><strong>Пол</strong>: ' . $gender . '</li>';
            $html .= '<li><strong>Школа</strong>: ' . $schoolId . '</li>';
            $html .= '<li><strong>Класс</strong>: ' . $class . '</li>';

            $html .= '</ul><br>';
        }


        // Формируем HTML для отчета

// Устанавливаем HTML-контент для Dompdf
        $dompdf->loadHtml($html);


        // Рендерим PDF
        $dompdf->render();

        // Генерируем имя файла для PDF
        $filename = $event->id . '_' . $event->name . '_report.pdf';

        // Отправляем PDF для скачивания пользователю
        $dompdf->stream($filename);
    }


    public function users(Request $request)
    {
        $users = User::all();
        return view('reports.users', ['users' => $users]);
    }

    public function downloadUserReport(Request $request, $userId)
    {

    }
}
