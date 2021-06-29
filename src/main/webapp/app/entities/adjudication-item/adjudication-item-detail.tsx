import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './adjudication-item.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AdjudicationItemDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const adjudicationItemEntity = useAppSelector(state => state.adjudicationItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adjudicationItemDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.detail.title">AdjudicationItem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{adjudicationItemEntity.id}</dd>
          <dt>
            <span id="outcome">
              <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.outcome">Outcome</Translate>
            </span>
          </dt>
          <dd>{adjudicationItemEntity.outcome}</dd>
          <dt>
            <span id="sequence">
              <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.sequence">Sequence</Translate>
            </span>
          </dt>
          <dd>{adjudicationItemEntity.sequence}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.adjudicationItem.claimResponse">Claim Response</Translate>
          </dt>
          <dd>{adjudicationItemEntity.claimResponse ? adjudicationItemEntity.claimResponse.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/adjudication-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/adjudication-item/${adjudicationItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdjudicationItemDetail;
