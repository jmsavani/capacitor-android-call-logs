export interface CapacitorCallLogsPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  checkPermission(): Promise<{ checkPermission: string }>;
  isEnabled(): Promise<{ isEnabled: boolean }>;
  getCallLogs(options: {
    date?: number;
    hour?: number;
    minute?: number;
    second?: number;
  }): Promise<any>;
}
