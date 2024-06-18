<?php

namespace Database\Seeders;

use App\Models\Reward;
use App\Models\Status;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Str;

class RewardSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        $rewards = [
            ['name' => 'Награда 1', 'description' => 'Описание награды 1', 'points' => 100],
            ['name' => 'Награда 2', 'description' => 'Описание награды 2', 'points' => 200],
            ['name' => 'Награда 3', 'description' => 'Описание награды 3', 'points' => 300],
            ['name' => 'Награда 4', 'description' => 'Описание награды 4', 'points' => 400],
            ['name' => 'Награда 5', 'description' => 'Описание награды 5', 'points' => 500],
        ];

        $status = Status::where('name', 'В наличии')->first();

        foreach ($rewards as $reward) {
            Reward::create([
                'id' => Str::uuid(),
                'name' => $reward['name'],
//                'description' => $reward['description'],
                'points_required' => $reward['points'],
                'quantity' => rand(1,10),
                'status_id' => $status->id,
            ]);
        }
    }
}
