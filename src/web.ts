import { WebPlugin } from '@capacitor/core';

import type { CapacitorCallLogsPlugin } from './definitions';

export class CapacitorCallLogsWeb extends WebPlugin implements CapacitorCallLogsPlugin {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  getCallLogs(_options: { fromDateTime: number; toDateTime: number }): Promise<any> {
    throw new Error('Method not implemented.');
  }
  checkPermission(): Promise<{ checkPermission: string }> {
    throw new Error('Method not implemented.');
  }
  isEnabled(): Promise<{ isEnabled: boolean }> {
    throw new Error('Method not implemented.');
  }
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
