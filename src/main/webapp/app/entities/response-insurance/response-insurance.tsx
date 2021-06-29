import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './response-insurance.reducer';
import { IResponseInsurance } from 'app/shared/model/response-insurance.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ResponseInsurance = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const responseInsuranceList = useAppSelector(state => state.responseInsurance.entities);
  const loading = useAppSelector(state => state.responseInsurance.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="response-insurance-heading" data-cy="ResponseInsuranceHeading">
        <Translate contentKey="hcpNphiesPortalApp.responseInsurance.home.title">Response Insurances</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.responseInsurance.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.responseInsurance.home.createLabel">Create new Response Insurance</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {responseInsuranceList && responseInsuranceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.responseInsurance.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.responseInsurance.notInforceReason">Not Inforce Reason</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.responseInsurance.inforce">Inforce</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.responseInsurance.benefitStart">Benefit Start</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.responseInsurance.benefitEnd">Benefit End</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.responseInsurance.coverage">Coverage</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.responseInsurance.coverageEligibilityResponse">
                    Coverage Eligibility Response
                  </Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {responseInsuranceList.map((responseInsurance, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${responseInsurance.id}`} color="link" size="sm">
                      {responseInsurance.id}
                    </Button>
                  </td>
                  <td>{responseInsurance.notInforceReason}</td>
                  <td>{responseInsurance.inforce ? 'true' : 'false'}</td>
                  <td>
                    {responseInsurance.benefitStart ? (
                      <TextFormat type="date" value={responseInsurance.benefitStart} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {responseInsurance.benefitEnd ? (
                      <TextFormat type="date" value={responseInsurance.benefitEnd} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {responseInsurance.coverage ? (
                      <Link to={`coverage/${responseInsurance.coverage.id}`}>{responseInsurance.coverage.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {responseInsurance.coverageEligibilityResponse ? (
                      <Link to={`coverage-eligibility-response/${responseInsurance.coverageEligibilityResponse.id}`}>
                        {responseInsurance.coverageEligibilityResponse.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${responseInsurance.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${responseInsurance.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${responseInsurance.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="hcpNphiesPortalApp.responseInsurance.home.notFound">No Response Insurances found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ResponseInsurance;
