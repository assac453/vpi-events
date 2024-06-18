<?php

namespace Database\Seeders;

use App\Models\Event;
use App\Models\User;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class UsersToEventSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        // Получите всех пользователей и мероприятия из базы данных
        $users = User::all();
        $events = Event::all();

        // Пройдитесь по каждому мероприятию и назначьте случайных пользователей, которые посетили это мероприятие
        foreach ($events as $event) {
            // Сгенерируйте случайное количество пользователей для мероприятия (от 1 до count($users))
            $randomUserCount = rand(1, count($users));

            // Получите случайные пользователи для мероприятия
            $randomUsers = $users->random($randomUserCount);

            // Присоедините пользователей к мероприятию
            foreach ($randomUsers as $user) {
                $event->users()->attach($user->id);
            }
        }
    }

}
