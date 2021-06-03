import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ListEligibilityPurposeEnum from './list-eligibility-purpose-enum';
import ListEligibilityPurposeEnumDetail from './list-eligibility-purpose-enum-detail';
import ListEligibilityPurposeEnumUpdate from './list-eligibility-purpose-enum-update';
import ListEligibilityPurposeEnumDeleteDialog from './list-eligibility-purpose-enum-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ListEligibilityPurposeEnumUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ListEligibilityPurposeEnumUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ListEligibilityPurposeEnumDetail} />
      <ErrorBoundaryRoute path={match.url} component={ListEligibilityPurposeEnum} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ListEligibilityPurposeEnumDeleteDialog} />
  </>
);

export default Routes;
