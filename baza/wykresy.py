import pandas as pd
import matplotlib.pyplot as plt
import sys
import os


def load_execution_times(filepath):
    with open(filepath, "r") as file:
        return pd.Series([int(line.strip()) for line in file.readlines()])


def plot_execution_times(files, output_file="execution_comparison.png"):
    plt.figure(figsize=(12, 6))

    for file_path in files:
        times = load_execution_times(file_path)
        label = os.path.basename(file_path)
        plt.plot(times, label=label, linewidth=2)
        print(f"Średnia arytmetyczna dla {label}: {times.mean():.2f} mikrosekund")

    plt.xlabel("Numer zapytania")
    plt.ylabel("Czas wykonywania (mikrosekundy)")
    plt.legend()
    plt.grid(True)
    plt.tight_layout()
    plt.savefig(output_file)
    print(f"Chart saved to {output_file}")


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python wykresy.py file1.log [file2.log ...]")
    else:
        plot_execution_times(sys.argv[1:])
