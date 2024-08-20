/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { OrderItem } from './OrderItem';
// @ts-ignore
import type { QuestionManageVO } from './QuestionManageVO';

export type Page_QuestionManageVO_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    // @ts-ignore
    orders?: Array<OrderItem>;
    pages?: number;
    // @ts-ignore
    records?: Array<QuestionManageVO>;
    searchCount?: boolean;
    size?: number;
    total?: number;
};
