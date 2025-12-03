const std = @import("std");
const aoc = @import("aoc.zig");
const allocator = std.heap.page_allocator;
const input_path = "/home/user/AoC_Inputs/2025/01";

fn partOne(input: []const u8) u32 {
	var dial: i32 = 50;
	var counter: u32 = 0;
	var turn: i32 = 0;
	var modifier: i32 = 1;
	for(input) |char| {
		switch (char) {
			'\n' => {
				//perform turning
				dial = @mod((dial + (turn * modifier)), 100);
				if(dial == 0) {
					counter += 1;
				}
				turn = 0;
			},
			'R' => {
				modifier = 1;
			},
			'L' => {
				modifier = -1;
			},
			else => {
				turn *= 10;
				turn += (char - 48);
			}
		}
	}

	//perform last turning
	dial = @mod((dial + (turn * modifier)), 100);
	if(dial == 0) {
		counter += 1;
	}
	turn = 0;

	return counter;
}

fn partTwo(input: []const u8) u32 {
	var dial: i32 = 50;
	var counter: u32 = 0;
	var turn: i32 = 0;
	var modifier: i32 = 1;
	for(input) |char| {
		switch (char) {
			'\n' => {
				//perform turning
				const at_zero: bool = dial == 0;
				counter += @abs(@divTrunc(modifier*turn, 100));
				dial += @rem(modifier*turn, 100);
				if(dial <= 0 and !at_zero) {
					counter += 1;
				}
				if(dial > 99) {
					counter += 1;
				}
				dial = @mod(dial, 100);
				turn = 0;
			},
			'R' => {
				modifier = 1;
			},
			'L' => {
				modifier = -1;
			},
			else => {
				turn *= 10;
				turn += (char - 48);
			}
		}
	}

	//perform last turning
	const at_zero: bool = dial == 0;
	counter += @abs(@divTrunc(modifier*turn, 100));
	dial += @rem(modifier*turn, 100);
	if(dial <= 0 and !at_zero) {
		counter += 1;
	}
	if(dial > 99) {
		counter += 1;
	}
	dial = @mod(dial, 100);
	turn = 0;

	return counter;
}


pub fn main() !void {
	const input: []const u8 = try aoc.readInput(input_path, allocator);
	const output_1 = partOne(input);
	const output_2 = partTwo(input);
	std.debug.print("Part 1: {}\nPart 2: {}\n", .{output_1, output_2});
}
