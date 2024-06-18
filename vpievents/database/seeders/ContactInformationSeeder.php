<?php

namespace Database\Seeders;

use App\Models\ContactInformation;
use App\Models\User;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Str;

class ContactInformationSeeder extends Seeder
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
                ContactInformation::create([
                    'id' => Str::uuid()->toString(),
                    'user_id' => $user->id,
                    'telegram' => '@' . explode('@', $email)[0],
                    'vk' => explode('@', $email)[0],
                    'phone' => '89048888888',
                ]);
            }
        }
    }
}
