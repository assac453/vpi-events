<?php

namespace Database\Seeders;

use App\Models\Event;
use App\Models\EventType;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class EventTypeToEventSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        $events = Event::all();
        $event_types = EventType::all();
        for ($i = 0; $i < $events->count(); $i++) {
            for ($j = 0; $j < $event_types->count(); $j++) {
                $events[$i]->event_type()->save($event_types[$j]);
            }
        }
    }
}
