<?php

namespace Database\Seeders;

use App\Models\AdditionalInformation;
use App\Models\User;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class AdditionalInformationSeeder extends Seeder
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
                AdditionalInformation::create([
                    'user_id' => $user->id,
                    'passport_series' => '1234',
                    'passport_number' => '123412',
                    'passport_issue_date' => '2023-01-01',
                    'registration_address' => 'г. Волжский ул. Набережная 72',
                    'residential_address' => 'г. Волжский ул. Набережная 72',
                    'disability' => false,
                    'orphan' => false,
                ]);
            }
        }
    }
}
