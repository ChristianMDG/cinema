package com.cinema.app.file.hash;

import com.cinema.app.PojaGenerated;

@PojaGenerated
public record FileHash(FileHashAlgorithm algorithm, String value) {}
