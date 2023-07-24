export interface CapacitorCallLogsPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  checkPermission(): Promise<{ checkPermission: string }>;
  isEnabled(): Promise<{ isEnabled: boolean }>;
  getCallLogs(options: {fromDateTime: number , toDateTime: number}): Promise<any>;
}
