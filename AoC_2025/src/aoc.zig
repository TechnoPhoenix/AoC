const std = @import("std");

pub fn readInput(path: []const u8, alloc: std.mem.Allocator) ![]const u8{
	const file = try std.fs.openFileAbsolute(path,.{});
	defer file.close();
	const output = try file.readToEndAlloc(alloc, 0xffffffff);
	return std.mem.trim(u8, output, " \n");
}
