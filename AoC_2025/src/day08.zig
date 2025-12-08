const std = @import("std");
const aoc = @import("aoc.zig");
const allocator = std.heap.page_allocator;
const input_path = "/home/user/AoC_Inputs/2025/08";

const Box = struct{
	x: u64,
	y: u64,
	z: u64
};

const Pair = struct{
	distance: u64,
	box_a: Box,
	box_b: Box
};

fn partOne(input: []const u8) u64 {
	var output: u64 = 1;
	var boxes: std.ArrayList(Box) = .empty;
	var iter = std.mem.splitSequence(u8, input, "\n");
	while(iter.next()) |line| {
		var box = Box{.x = 0, .y = 0, .z = 0};
		var toggle: bool = true;
		var number: u64 = 0;
		for(line) |char| {
			if(char == ',' and toggle) {
				box.x = number;
				number = 0;
				toggle = false;
			} else if(char == ',' and !toggle) {
				box.y = number;
				number = 0;
			} else {
				number *= 10;
				number += char-48;
			}
		}
		box.z = number;
		boxes.append(allocator, box) catch {
			std.debug.print("failed appending to arraylist", .{}); };
	}

	var distances: std.ArrayList(Pair) = .empty;
	for(0..boxes.items.len) |i| {
		for(i+1..boxes.items.len) |j| {
			const a = @max(boxes.items[i].x, boxes.items[j].x) - @min(boxes.items[i].x, boxes.items[j].x);
			const b = @max(boxes.items[i].y, boxes.items[j].y) - @min(boxes.items[i].y, boxes.items[j].y);
			const c = @max(boxes.items[i].z, boxes.items[j].z) - @min(boxes.items[i].z, boxes.items[j].z);
			const dist: u64 = @intFromFloat(@sqrt(@as(f64, @floatFromInt(std.math.pow(u64, a, 2)+std.math.pow(u64, b, 2)+std.math.pow(u64, c, 2)))));
			distances.append(allocator,Pair{.distance = dist, .box_a = boxes.items[i], .box_b = boxes.items[j]}) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		}
	}

	var groups: std.ArrayList(std.ArrayList(Box)) = .empty;
	var x: u64 = 0;
	while(x < 1000) : (x += 1) {
		var small: u64 = std.math.maxInt(u64);
		var index: u64 = 0;
		for(distances.items, 0..) |distance, ind| {
			if(distance.distance < small) {
				index = ind;
				small = distance.distance;
			}
		}
		const con = distances.orderedRemove(index);
		var a_index: u64 = std.math.maxInt(u64);
		var b_index: u64 = std.math.maxInt(u64);
		for(groups.items, 0..) |group, ind| {
			for(group.items) |box| {
				if(std.meta.eql(box, con.box_a)) {
					a_index = ind;
				}
				if(std.meta.eql(box, con.box_b)) {
					b_index = ind;
				}
			}
		}
		if(a_index != std.math.maxInt(u64) and b_index != std.math.maxInt(u64)) {
			if(a_index != b_index) {
				var temp = groups.items[b_index];
				const group = temp.toOwnedSlice(allocator) catch {
					std.debug.print("failed arraylist to slice", .{});
					return 0; };
				groups.items[a_index].appendSlice(allocator, group) catch {
					std.debug.print("failed appending to arraylist", .{}); };
				_ = groups.orderedRemove(b_index);
				allocator.free(group);
			}
		} else if(a_index != std.math.maxInt(u64) and b_index == std.math.maxInt(u64)) {
			groups.items[a_index].append(allocator, con.box_b) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		} else if(a_index == std.math.maxInt(u64) and b_index != std.math.maxInt(u64)) {
			groups.items[b_index].append(allocator, con.box_a) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		} else if(a_index == std.math.maxInt(u64) and b_index == std.math.maxInt(u64)) {
			var temp: std.ArrayList(Box) = .empty;
			temp.append(allocator, con.box_a) catch {
				std.debug.print("failed appending to arraylist", .{}); };
			temp.append(allocator, con.box_b) catch {
				std.debug.print("failed appending to arraylist", .{}); };
			groups.append(allocator, temp) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		}
	}

	var y: u64 = 0;
	while(y < 3) {
		var biggest: u64 = 1;
		var index: u64 = 0;
		for(groups.items, 0..) |group, ind| {
			const size: u64 = group.items.len;
			if(size > biggest) {
				biggest = size;
				index = ind;
			}
		}
		output *= biggest;
		_ = groups.orderedRemove(index);
		y += 1;
	}

	return output;
}

fn partTwo(input: []const u8) u64 {
	var output: u64 = 1;
	var boxes: std.ArrayList(Box) = .empty;
	var iter = std.mem.splitSequence(u8, input, "\n");
	while(iter.next()) |line| {
		var box = Box{.x = 0, .y = 0, .z = 0};
		var toggle: bool = true;
		var number: u64 = 0;
		for(line) |char| {
			if(char == ',' and toggle) {
				box.x = number;
				number = 0;
				toggle = false;
			} else if(char == ',' and !toggle) {
				box.y = number;
				number = 0;
			} else {
				number *= 10;
				number += char-48;
			}
		}
		box.z = number;
		boxes.append(allocator, box) catch {
			std.debug.print("failed appending to arraylist", .{}); };
	}

	var distances: std.ArrayList(Pair) = .empty;
	for(0..boxes.items.len) |i| {
		for(i+1..boxes.items.len) |j| {
			const a = @max(boxes.items[i].x, boxes.items[j].x) - @min(boxes.items[i].x, boxes.items[j].x);
			const b = @max(boxes.items[i].y, boxes.items[j].y) - @min(boxes.items[i].y, boxes.items[j].y);
			const c = @max(boxes.items[i].z, boxes.items[j].z) - @min(boxes.items[i].z, boxes.items[j].z);
			const dist: u64 = @intFromFloat(@sqrt(@as(f64, @floatFromInt(std.math.pow(u64, a, 2)+std.math.pow(u64, b, 2)+std.math.pow(u64, c, 2)))));
			distances.append(allocator,Pair{.distance = dist, .box_a = boxes.items[i], .box_b = boxes.items[j]}) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		}
	}

	var groups: std.ArrayList(std.ArrayList(Box)) = .empty;
	var last_con: Pair = Pair{.box_a = Box{.x=0, .y=0, .z=0}, .box_b = Box{.x=0, .y=0, .z=0}, .distance = 0};
	while(true) {
		var small: u64 = std.math.maxInt(u64);
		var index: u64 = 0;
		for(distances.items, 0..) |distance, ind| {
			if(distance.distance < small) {
				index = ind;
				small = distance.distance;
			}
		}
		const con = distances.orderedRemove(index);
		last_con = con;
		var a_index: u64 = std.math.maxInt(u64);
		var b_index: u64 = std.math.maxInt(u64);
		for(groups.items, 0..) |group, ind| {
			for(group.items) |box| {
				if(std.meta.eql(box, con.box_a)) {
					a_index = ind;
				}
				if(std.meta.eql(box, con.box_b)) {
					b_index = ind;
				}
			}
		}
		if(a_index != std.math.maxInt(u64) and b_index != std.math.maxInt(u64)) {
			if(a_index != b_index) {
				var temp = groups.items[b_index];
				const group = temp.toOwnedSlice(allocator) catch {
					std.debug.print("failed arraylist to slice", .{});
					return 0; };
				groups.items[a_index].appendSlice(allocator, group) catch {
					std.debug.print("failed appending to arraylist", .{}); };
				_ = groups.orderedRemove(b_index);
				allocator.free(group);
			}
		} else if(a_index != std.math.maxInt(u64) and b_index == std.math.maxInt(u64)) {
			groups.items[a_index].append(allocator, con.box_b) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		} else if(a_index == std.math.maxInt(u64) and b_index != std.math.maxInt(u64)) {
			groups.items[b_index].append(allocator, con.box_a) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		} else if(a_index == std.math.maxInt(u64) and b_index == std.math.maxInt(u64)) {
			var temp: std.ArrayList(Box) = .empty;
			temp.append(allocator, con.box_a) catch {
				std.debug.print("failed appending to arraylist", .{}); };
			temp.append(allocator, con.box_b) catch {
				std.debug.print("failed appending to arraylist", .{}); };
			groups.append(allocator, temp) catch {
				std.debug.print("failed appending to arraylist", .{}); };
		}

		if(groups.items.len == 1 and groups.items[0].items.len == boxes.items.len) {
			break;
		}
	}

	output = last_con.box_a.x * last_con.box_b.x;

	return output;
}


pub fn main() !void {
	const input: []const u8 = try aoc.readInput(input_path, allocator);
	const output_1 = partOne(input);
	const output_2 = partTwo(input);
	std.debug.print("Part 1: {}\nPart 2: {}\n", .{output_1, output_2});
}
