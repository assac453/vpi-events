<?php

namespace Database\Seeders;

// use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     */
    public function run(): void
    {
        $this->call([
            RolesTableSeeder::class,
            SchoolSeeder::class,
            UserSeeder::class,
            PersonalInformationSeeder::class,
            AdditionalInformationSeeder::class,
            ContactInformationSeeder::class,
            SchoolInformationSeeder::class,
            StatusesSeeder::class,
            EventTypeSeeder::class,
            EventSeeder::class,
            RequestStatusSeeder::class,
//            UsersToEventSeeder::class,
            RewardSeeder::class,
            RequestSeeder::class,

        ]);
        // \App\Models\User::factory(10)->create();

        // \App\Models\User::factory()->create([
        //     'name' => 'Test User',
        //     'email' => 'test@example.com',
        // ]);
    }
}
