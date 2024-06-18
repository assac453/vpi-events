<?php

namespace App\Models;

// use Illuminate\Contracts\Auth\MustVerifyEmail;
use Carbon\Carbon;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Illuminate\Support\Str;
use Laravel\Sanctum\HasApiTokens;

class User extends Authenticatable
{
    use HasApiTokens, HasFactory, Notifiable;

    /**
     * The attributes that are mass assignable.
     *
     * @var array<int, string>
     */
    protected $fillable = [
        'id',
        'email',
        'password',
        'firebase_token',
        'points',
    ];
    protected $keyType = 'string'; // Добавьте это
    public $incrementing = false; // Добавьте это


    /**
     * The attributes that should be hidden for serialization.
     *
     * @var array<int, string>
     */
    protected $hidden = [
        'password',
        'remember_token',
    ];

    public function roles()
    {
        return $this->belongsTo(Role::class);
    }

    public function personalInformation()
    {
        return $this->hasOne(PersonalInformation::class);
    }

    public function additionalInformation()
    {
        return $this->hasOne(AdditionalInformation::class);
    }

    public function contactInformation()
    {
        return $this->hasOne(ContactInformation::class);
    }

    public function schoolInformation()
    {
        return $this->hasOne(SchoolInformation::class);
    }

    /**
     * The attributes that should be cast.
     *
     * @var array<string, string>
     */
    protected $casts = [
        'email_verified_at' => 'datetime',
        'password' => 'hashed',
    ];

    public function notifications()
    {
        return $this->belongsToMany(Notification::class, 'notifications');
    }
    public function events()
    {
        return $this->belongsToMany(Event::class);
    }

    protected static function boot()
    {
        parent::boot();

        static::creating(function ($model) {
            if (empty($model->id)) {
                $model->id = Str::uuid();
            }
        });
    }

    public function getGenderAttribute()
    {
        return $this->personalInformation->gender;
    }

    public function getAgeAttribute()
    {
        return Carbon::parse($this->personalInformation->birth_date)->age;
    }

    public function getCurrentClassAttribute()
    {
        $currentYear = Carbon::now()->year;
        $startYear = Carbon::parse($this->personalInformation->start_year)->year; // Assuming you have a start_year field in personalInformation
        $currentClass = ($currentYear - $startYear) + 1; // Assuming the class starts from 1
        return $currentClass;
    }


}
