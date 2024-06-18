<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Str;

class Status extends Model
{
    use HasFactory;

    protected $fillable = ['name'];
    protected $keyType = 'string'; // Добавьте это
    public $incrementing = false; // Добавьте это


    public static function boot()
    {
        parent::boot();

        static::creating(function ($model) {
            if (empty($model->id)) {
                $model->id = (string) Str::uuid();
            }
        });
    }

    public function rewards()
    {
        return $this->hasMany(Reward::class);
    }
}
