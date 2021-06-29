import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './adjudication-detail-item.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AdjudicationDetailItemDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const adjudicationDetailItemEntity = useAppSelector(state => state.adjudicationDetailItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="adjudicationDetailItemDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.adjudicationDetailItem.detail.title">AdjudicationDetailItem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{adjudicationDetailItemEntity.id}</dd>
          <dt>
            <span id="sequence">
              <Translate contentKey="hcpNphiesPortalApp.adjudicationDetailItem.sequence">Sequence</Translate>
            </span>
          </dt>
          <dd>{adjudicationDetailItemEntity.sequence}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.adjudicationDetailItem.adjudicationItem">Adjudication Item</Translate>
          </dt>
          <dd>{adjudicationDetailItemEntity.adjudicationItem ? adjudicationDetailItemEntity.adjudicationItem.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/adjudication-detail-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/adjudication-detail-item/${adjudicationDetailItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AdjudicationDetailItemDetail;
