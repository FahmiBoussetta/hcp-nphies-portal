import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PaymentNotice from './payment-notice';
import PaymentNoticeDetail from './payment-notice-detail';
import PaymentNoticeUpdate from './payment-notice-update';
import PaymentNoticeDeleteDialog from './payment-notice-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PaymentNoticeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PaymentNoticeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PaymentNoticeDetail} />
      <ErrorBoundaryRoute path={match.url} component={PaymentNotice} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PaymentNoticeDeleteDialog} />
  </>
);

export default Routes;
