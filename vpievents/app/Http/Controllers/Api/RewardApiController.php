<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Reward;
use Illuminate\Http\Request;

class RewardApiController extends Controller
{
    public function index()
    {
        $rewards = Reward::all();
        return response()->json($rewards);
    }
}
