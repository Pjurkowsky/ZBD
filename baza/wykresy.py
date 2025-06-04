import pandas as pd
import matplotlib.pyplot as plt
import sys
import os


def load_execution_times(filepath):
    with open(filepath, "r") as file:
        return pd.Series([int(line.strip()) for line in file.readlines()])


def plot_execution_times(files, output_file="execution_comparison.png"):
    plt.figure(figsize=(12, 6))

    overall_min = float("inf")
    overall_max = float("-inf")

    for file_path in files:
        times = load_execution_times(file_path)
        label = os.path.basename(file_path)

        mean_val = times.mean()
        median_val = times.median()
        min_val = times.min()
        max_val = times.max()

        overall_min = min(overall_min, min_val)
        overall_max = max(overall_max, max_val)

        plt.plot(
            times,
            label=(f"{label}"),
            linewidth=2,
        )

        print(f"{label} – {mean_val:.2f} {median_val:.2f} {min_val:.2f} {max_val:.2f}")

    plt.xlabel("Numer zapytania")
    plt.ylabel("Czas wykonywania (mikrosekundy)")
    plt.legend()
    plt.grid(True)

    plt.ylim(bottom=min(0, overall_min))
    plt.xlim(left=0)

    plt.tight_layout()
    plt.savefig(output_file)
    print(f"Chart saved to {output_file}")


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python wykresy.py file1.log [file2.log ...]")
    else:
        plot_execution_times(sys.argv[1:])
