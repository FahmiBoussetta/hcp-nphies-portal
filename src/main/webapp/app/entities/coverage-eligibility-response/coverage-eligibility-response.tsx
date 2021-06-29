import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './coverage-eligibility-response.reducer';
import { ICoverageEligibilityResponse } from 'app/shared/model/coverage-eligibility-response.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CoverageEligibilityResponse = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const coverageEligibilityResponseList = useAppSelector(state => state.coverageEligibilityResponse.entities);
  const loading = useAppSelector(state => state.coverageEligibilityResponse.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="coverage-eligibility-response-heading" data-cy="CoverageEligibilityResponseHeading">
        <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.home.title">Coverage Eligibility Responses</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.home.createLabel">
              Create new Coverage Eligibility Response
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {coverageEligibilityResponseList && coverageEligibilityResponseList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.system">System</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.parsed">Parsed</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.outcome">Outcome</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.serviced">Serviced</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.servicedEnd">Serviced End</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.disposition">Disposition</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.notInforceReason">Not Inforce Reason</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.patient">Patient</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.insurer">Insurer</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {coverageEligibilityResponseList.map((coverageEligibilityResponse, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${coverageEligibilityResponse.id}`} color="link" size="sm">
                      {coverageEligibilityResponse.id}
                    </Button>
                  </td>
                  <td>{coverageEligibilityResponse.value}</td>
                  <td>{coverageEligibilityResponse.system}</td>
                  <td>{coverageEligibilityResponse.parsed}</td>
                  <td>{coverageEligibilityResponse.outcome}</td>
                  <td>
                    {coverageEligibilityResponse.serviced ? (
                      <TextFormat type="date" value={coverageEligibilityResponse.serviced} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {coverageEligibilityResponse.servicedEnd ? (
                      <TextFormat type="date" value={coverageEligibilityResponse.servicedEnd} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{coverageEligibilityResponse.disposition}</td>
                  <td>{coverageEligibilityResponse.notInforceReason}</td>
                  <td>
                    {coverageEligibilityResponse.patient ? (
                      <Link to={`patient/${coverageEligibilityResponse.patient.id}`}>{coverageEligibilityResponse.patient.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {coverageEligibilityResponse.insurer ? (
                      <Link to={`organization/${coverageEligibilityResponse.insurer.id}`}>{coverageEligibilityResponse.insurer.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${coverageEligibilityResponse.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${coverageEligibilityResponse.id}/edit`}
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
                        to={`${match.url}/${coverageEligibilityResponse.id}/delete`}
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
              <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.home.notFound">
                No Coverage Eligibility Responses found
              </Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default CoverageEligibilityResponse;
