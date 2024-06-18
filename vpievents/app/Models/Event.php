<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Str;

class Event extends Model
{
    use HasFactory;

    public $incrementing = false;
    protected $keyType = 'string';
    protected $table = 'event_models';
    protected $fillable = ['name', 'address', 'longitude', 'latitude', 'description', 'points', 'qrcode', 'image', 'begin', 'end','event_type_id'];
    protected static function boot()
    {
        parent::boot();
        static::creating(function ($model) {
            if (empty($model->{$model->getKeyName()})) {
                $model->{$model->getKeyName()} = (string) Str::uuid();
            }
        });
    }

    public function event_type(){
        return $this->belongsTo(EventType::class);
    }

    public function users()
    {
        return $this->belongsToMany(User::class, 'event_user', 'event_id', 'user_id');
    }

}
