import pandas as pd
import matplotlib.pyplot as plt
import sys

def load_execution_times(filepath):
    with open(filepath, "r") as file:
        return pd.Series([int(line.strip()) for line in file.readlines()])

def plot_execution_times(file1, file2, output_file="execution_comparison.png"):
    times1 = load_execution_times(file1)
    times2 = load_execution_times(file2)
    print(times1, times2)

    df = pd.DataFrame({
        "File 1": times1,
        "File 2": times2
    })

    plt.figure(figsize=(12, 6))
    plt.plot(df["File 1"], label="File 1", linewidth=2)
    plt.plot(df["File 2"], label="File 2", linewidth=2)
    plt.title("SQL Statement Execution Time Comparison")
    plt.xlabel("Statement Index")
    plt.ylabel("Execution Time (microseconds)")
    plt.legend()
    plt.grid(True)
    plt.tight_layout()
    plt.savefig(output_file)
    print(f"Chart saved to {output_file}")

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python wykresy.py file1.log file2.log")
    else:
        plot_execution_times(sys.argv[1], sys.argv[2])
