const std = @import("std");
const aoc = @import("aoc.zig");
const allocator = std.heap.page_allocator;
const input_path = "/home/user/AoC_Inputs/2025/09";

const Tile = struct{
	x: i64,
	y: i64
};

const Line = struct{
	pos: i64,
	min: i64,
	max: i64,
	axis: u8
};

fn partOne(input: []const u8) u64 {
	var output: u64 = 0;
	var tiles: std.ArrayList(Tile) = .empty;
	var iter = std.mem.splitAny(u8, input, "\n");
	while(iter.next()) |line| {
		var num_a: i64 = 0;
		var num_b: i64 = 0;
		var toggle = false;
		for(line) |char| {
			if(toggle) {
				num_b *= 10;
				num_b += char-48;
			} else {
				if(char == ',') {
					toggle = true;
				} else {
					num_a *= 10;
					num_a += char-48;
				}
			}
		}
		const temp = Tile{.x = num_a, .y = num_b};
		tiles.append(allocator, temp) catch {
			std.debug.print("failed appending to arraylist", .{}); };
	}

	for(tiles.items) |tile_a| {
		for(tiles.items) |tile_b| {
			const area: u64 = @intCast((@abs(tile_a.x - tile_b.x)+1) * (@abs(tile_a.y - tile_b.y)+1));
			output = @max(area, output);
		}
	}

	return output;
}

fn partTwo(input: []const u8) u64 {
	var output: u64 = 0;
	var tiles: std.ArrayList(Tile) = .empty;
	var lines: std.ArrayList(Line) = .empty;
	var iter = std.mem.splitAny(u8, input, "\n");
	while(iter.next()) |line| {
		var num_a: i64 = 0;
		var num_b: i64 = 0;
		var toggle = false;
		for(line) |char| {
			if(toggle) {
				num_b *= 10;
				num_b += char-48;
			} else {
				if(char == ',') {
					toggle = true;
				} else {
					num_a *= 10;
					num_a += char-48;
				}
			}
		}
		const temp = Tile{.x = num_a, .y = num_b};
		tiles.append(allocator, temp) catch {
			std.debug.print("failed appending to arraylist", .{}); };
	}

	for(0..tiles.items.len) |i| {
		if(@mod(i, 2) == 0) {
			const b1: i64 = tiles.items[i].y;
			const b2: i64 = tiles.items[@mod(i+1, tiles.items.len)].y;
			const temp = Line{.pos = tiles.items[i].x, .min = @min(b1, b2), .max = @max(b1, b2), .axis = 'x'};
			lines.append(allocator, temp) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		} else if(@mod(i, 2) == 1) {
			const b1 = tiles.items[i].x;
			const b2 = tiles.items[@mod(i+1, tiles.items.len)].x;
			const temp = Line{.pos = tiles.items[i].y, .min = @min(b1, b2), .max = @max(b1, b2), .axis = 'y'};
			lines.append(allocator, temp) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		} else {
			unreachable;
		}
	}

	for(0..tiles.items.len) |i| {
		for(i+1..tiles.items.len) |j| {
			const area: u64 = @intCast((@abs(tiles.items[i].x - tiles.items[j].x)+1) * (@abs(tiles.items[i].y - tiles.items[j].y)+1));
			if(area > output) {
				var valid: bool = true;
				const min_x: i64 = @min(tiles.items[i].x, tiles.items[j].x);
				const max_x: i64 = @max(tiles.items[i].x, tiles.items[j].x);
				const min_y: i64 = @min(tiles.items[i].y, tiles.items[j].y);
				const max_y: i64 = @max(tiles.items[i].y, tiles.items[j].y);
				for(lines.items) |line| {
					if(line.axis == 'x') {
						if(	(min_x < line.pos and line.pos < max_x) and
							!(line.min >= max_y or line.max <= min_y)) {
							valid = false;
							break;
						}
					} else if(line.axis == 'y') {
						if(	(min_y < line.pos and line.pos < max_y) and
				 			!(line.min >= max_x or line.max <= min_x)) {
							valid = false;
							break;
						}
					} else {
						unreachable;
					}
				}
				for(tiles.items) |tile| {
					if(min_x < tile.x and tile.x < max_x and min_y < tile.y and tile.y < max_y) {
						valid = false;
						break;
					}
				}
				if(valid) {
					output = area;
				}
			}
		}
	}

	return output;
}


pub fn main() !void {
	const input: []const u8 = try aoc.readInput(input_path, allocator);
	const output_1 = partOne(input);
	const output_2 = partTwo(input);
	std.debug.print("Part 1: {}\nPart 2: {}\n", .{output_1, output_2});
}
