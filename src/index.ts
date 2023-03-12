import { registerPlugin } from '@capacitor/core';

import type { CapacitorCallLogsPlugin } from './definitions';

const CapacitorCallLogs = registerPlugin<CapacitorCallLogsPlugin>(
  'CapacitorCallLogs',
  {
    web: () => import('./web').then(m => new m.CapacitorCallLogsWeb()),
  },
);

export * from './definitions';
export { CapacitorCallLogs };
