<?php

namespace Database\Seeders;

use App\Models\SchoolInformation;
use App\Models\User;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Str;
use Ramsey\Uuid\Type\Integer;

class SchoolInformationSeeder extends Seeder
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
                SchoolInformation::create([
                    'id' => Str::uuid()->toString(),
                    'user_id' => $user->id,
                    'school_id' => 1, // ID of some school
                    'class' => 10,
                ]);
            }
        }
    }
}
