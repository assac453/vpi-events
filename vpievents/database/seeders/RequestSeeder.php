<?php

namespace Database\Seeders;

use App\Models\Request;
use App\Models\Reward;
use App\Models\StatusRequest;
use App\Models\User;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Str;

class RequestSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        // Получаем пользователей, награды и статусы
        $users = User::all();
        $rewards = Reward::all();
        $statuses = StatusRequest::all();


        // Проверяем, есть ли нужные данные для генерации заявок
        if ($users->isEmpty() || $rewards->isEmpty() || $statuses->isEmpty()) {
            $this->command->error('Not enough data to seed requests. Ensure that users, rewards, and statuses exist.');
            return;
        }

        // Создаем заявки для пользователей
        foreach ($users as $user) {
            Request::create([
                'id' => Str::uuid(),
                'user_id' => $user->id,
                'reward_id' => $rewards->random()->id,
                'status_id' => $statuses->random()->id,
//                'created_at' => now(),
                'closed_at' => rand(0, 1) ? now() : null, // случайным образом закрываем часть заявок
            ]);
        }
    }
}
