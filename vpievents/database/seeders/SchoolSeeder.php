<?php

namespace Database\Seeders;

use App\Models\School;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class SchoolSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        School::create([
            'name' => 'School 1',
            'address' => 'School Street, 1',
            'longitude' => 37.6173,
            'latitude' => 55.7558,
        ]);
    }
}
