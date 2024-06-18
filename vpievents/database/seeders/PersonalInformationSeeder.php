<?php

namespace Database\Seeders;

use App\Models\PersonalInformation;
use App\Models\User;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class PersonalInformationSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        $users = [
            'tarasov.alexander.artemovich@example.com',
            'matveeva.polina.kirillovna@example.com',
            'gracheva.arina.matveevna@example.com',
            'simonov.david.semenovich@example.com',
            'tokarev.timofey.igorevich@example.com',
            'vinogradov.daniil.igorevich@example.com',
            'fokina.varvara.maximovna@example.com',
            'kovaleva.ailin.glebovna@example.com',
            'morozova.zoya.alexandrovna@example.com',
            'ivanov.dmitry.sergeevich@example.com',
        ];

        foreach ($users as $email) {
            $user = User::where('email', $email)->first();

            if ($user) {
                PersonalInformation::create([
                    'user_id' => $user->id,
                    'birth_date' => '2009-01-01',
                    'last_name' => explode('.', explode('@', $email)[0])[0],
                    'first_name' => explode('.', explode('@', $email)[0])[1],
                    'middle_name' => explode('.', explode('@', $email)[0])[2],
                    'gender' => 'муж', // Пример, можно сделать логику определения пола
                ]);
            }
        }
    }
}
