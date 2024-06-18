<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\Reward;
use App\Models\Status;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Str;

class RewardController extends Controller
{
    public function index()
    {
        $rewards = Reward::with('status')->get();
        return view('rewards.index', compact('rewards'));
    }

    public function create()
    {
        $statuses = Status::all();
        return view('rewards.create', compact('statuses'));
    }

    public function store(Request $request)
    {
        $request->validate([
            'name' => 'required|string|max:255',
            'points_required' => 'required|integer',
            'image' => 'nullable|image|mimes:jpeg,png,jpg,gif,svg|max:2048',
            'quantity' => 'required|integer',
            'status_id' => 'required|uuid|exists:statuses,id',
        ]);

        $imagePath = $request->file('image') ? $request->file('image')->store('images', 'public') : null;

        $reward = new Reward();
        $reward->id = Str::uuid();
        $reward->name = $request->name;
        $reward->points_required = $request->points_required;
        $reward->image = $imagePath;
        $reward->quantity = $request->quantity;
        $reward->status_id = $request->status_id;
        $reward->save();

        return redirect()->route('rewards.index')->with('success', 'Награда успешно создана.');
    }

    public function show($id)
    {
        $reward = Reward::findOrFail($id);
        return view('rewards.show', compact('reward'));
    }

    public function edit($id)
    {
        $reward = Reward::findOrFail($id);
        $statuses = Status::all();
        return view('rewards.edit', compact('reward', 'statuses'));
    }

    public function update(Request $request, $id)
    {
        $request->validate([
            'name' => 'required|string|max:255',
            'points_required' => 'required|integer',
            'image' => 'nullable|image|mimes:jpeg,png,jpg,gif,svg|max:2048',
            'quantity' => 'required|integer',
            'status_id' => 'required|uuid|exists:statuses,id',
        ]);

        $reward = Reward::findOrFail($id);

        $imagePath = $reward->image;
        if ($request->hasFile('image')) {
            if ($imagePath) {
                Storage::disk('public')->delete($imagePath);
            }
            $imagePath = $request->file('image')->store('images', 'public');
        }

        $reward->name = $request->name;
        $reward->points_required = $request->points_required;
        $reward->image = $imagePath;
        $reward->quantity = $request->quantity;
        $reward->status_id = $request->status_id;
        $reward->save();

        return redirect()->route('rewards.index')->with('success', 'Награда успешно обновлена.');
    }

    public function destroy($id)
    {
        $reward = Reward::findOrFail($id);
        if ($reward->image) {
            Storage::disk('public')->delete($reward->image);
        }

        $reward->delete();

        return redirect()->route('rewards.index')->with('success', 'Награда успешно удалена.');
    }
}
