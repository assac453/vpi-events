<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class UserReward extends Model
{
    use HasFactory;

    protected $table = 'user_rewards';
    protected $fillable = ['user_id', 'reward_id', 'status_id', 'requested_at', 'closed_at'];

    public function user()
    {
        return $this->belongsTo(User::class);
    }

    public function reward()
    {
        return $this->belongsTo(Reward::class);
    }

    public function status()
    {
        return $this->belongsTo(Status::class);
    }
}
