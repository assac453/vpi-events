<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Str;

class EventType extends Model
{
    use HasFactory;


    public $incrementing = false;
    protected $keyType = 'string';

    protected $table = 'event_types';
    protected $fillable = ['name'];

    public function events(){
        return $this->hasMany(Event::class);
    }

    protected static function boot()
    {
        parent::boot();

        static::creating(function ($model) {
            if (empty($model->{$model->getKeyName()})) {
                $model->{$model->getKeyName()} = (string) Str::uuid();
            }
        });
    }
}
